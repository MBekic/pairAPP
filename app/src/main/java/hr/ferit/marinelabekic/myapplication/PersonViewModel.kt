package hr.ferit.marinelabekic.myapplication
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


data class Person(
    var name: String ="",
    var id: Int=0,
    var pairId: Int =0
)

class PersonViewModel : ViewModel() {
    private val db = Firebase.firestore
    val people = mutableStateListOf<Person>()
    private var currentId = 0
    var rememberedPerson: Person? = null
        private set
    init {
        fetchDatabaseData()
    }
    private fun fetchDatabaseData() {
        db.collection("people")
            .get()
            .addOnSuccessListener { result ->
                var maxId = 0
                for (data in result.documents) {
                    val person = data.toObject(Person::class.java)
                    if (person != null) {
                        person.id = data.getLong("id")?.toInt()!!
                        people.add(person)
                        maxId = maxOf(maxId, person.id)
                    }
                }
                currentId = maxId + 1
                if (people.isNotEmpty()) {
                    rememberedPerson = people.first()
                }
            }

    }

    fun addPerson(name: String) {
        val db = Firebase.firestore
        val newPerson = Person(name = name, id = currentId, pairId = 0)
        Log.d("addPerson", "Adding person with id: ${newPerson.id}")
        currentId++
        db.collection("people")
            .add(newPerson)
            .addOnSuccessListener {
                Log.d("addPerson", "Person successfully added to Firestore")
                people.add(newPerson)
            }
            .addOnFailureListener { e ->
                Log.e("addPerson", "Error adding person", e)
            }
    }

    fun removePerson(person: Person) {
        val db = Firebase.firestore

        db.collection("people")
            .whereEqualTo("name", person.name)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {

                    val documentId = querySnapshot.documents.first().id
                    db.collection("people").document(documentId).delete()
                        .addOnSuccessListener {
                            Log.d("removePerson", "Person successfully deleted from Firestore")
                            people.remove(person)
                        }
                        .addOnFailureListener { e ->
                            Log.e("removePerson", "Error deleting person", e)
                        }
                } else {
                    Log.d("removePerson", "No person found with that name")
                }
            }
            .addOnFailureListener { e ->
                Log.e("removePerson", "Error finding person", e)
            }
    }

    fun removeInvalidPeople() {
        people.removeAll { it.id == 0 }
    }

    fun rememberPerson(person: Person) {
        rememberedPerson = person
    }

    fun createRandomPairs() {
        removeInvalidPeople()

        var validPeople = people.toMutableList()
        val excludedPeople = mutableListOf<Person>()

        validPeople.shuffle()

        val pairs = validPeople.chunked(2)
        for (pair in pairs) {
            if (pair.size == 2) {
                pair[0].pairId = pair[1].id
                pair[1].pairId = pair[0].id
            } else if (pair.size == 1) {
                val singlePerson = pair[0]
                singlePerson.pairId = singlePerson.id
                excludedPeople.add(singlePerson)
                validPeople.remove(singlePerson)
            }
        }

        val allPeople = validPeople + excludedPeople

        for (person in allPeople) {
            val db = Firebase.firestore
            val personRef = db.collection("people").whereEqualTo("id", person.id)

            personRef.get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    val docId = documents.documents.first().id
                    db.collection("people").document(docId).update("pairId", person.pairId)
                        .addOnSuccessListener {
                            Log.d("RandomPairs", "PairId updated for ${person.name}")
                        }
                        .addOnFailureListener { e ->
                            Log.e("RandomPairs", "Error updating pairId for ${person.name}", e)
                        }
                }
            }
        }
        people.clear()
        people.addAll(allPeople)
    }

    fun shuffleAndAssignRandomIds() {
        removeInvalidPeople()

        val validPeople = people.toMutableList()
        val availableIds = validPeople.map { it.id }
        val derangedIds = generateDerangement(availableIds)

        for (i in validPeople.indices) {
            validPeople[i] = validPeople[i].copy(pairId = derangedIds[i])
        }

        val db = Firebase.firestore
        validPeople.forEach { person ->
            db.collection("people").whereEqualTo("id", person.id).get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val docId = documents.documents.first().id
                        db.collection("people").document(docId).update("pairId", person.pairId)
                            .addOnSuccessListener {
                                Log.d("shuffleAndAssignRandomIds", "pairId for ${person.name} updated to ${person.pairId}")
                            }
                            .addOnFailureListener { e ->
                                Log.e("shuffleAndAssignRandomIds", "Error updating pairId for ${person.name}", e)
                            }
                    }
                }
        }
        people.clear()
        people.addAll(validPeople)
    }

    private fun <T> generateDerangement(list: List<T>): List<T> {
        val result = list.toMutableList()

        do {
            result.shuffle()
        } while (result.indices.any { result[it] == list[it] })

        return result
    }

    fun isNameAlreadyTaken(name: String): Boolean {
        return people.any { it.name.equals(name, ignoreCase = true) }
    }
    fun searchPeople(query: String): List<Person> {
        return people.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}
