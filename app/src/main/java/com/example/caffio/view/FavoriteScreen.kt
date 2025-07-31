package com.example.caffio.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.caffio.model.Favoritecoffee
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun FavoriteScreen() {
    val databaseRef = FirebaseDatabase.getInstance().getReference("favorites")
    val favoriteList = remember { mutableStateListOf<Favoritecoffee>() }

    // Observe real-time changes
    LaunchedEffect(Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                favoriteList.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Favoritecoffee::class.java)
                    item?.let { favoriteList.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load favorites", error.toException())
            }
        })
    }

    // UI to display the favorite list
    Column {
        Text(
            text = "Favorite List",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {
            items(favoriteList) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = item.name,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // Save new favorite for testing
        Button(
            onClick = {
                val newId = databaseRef.push().key ?: return@Button
                val newItem = Favoritecoffee(id = newId, name = "Cappuccino")
                databaseRef.child(newId).setValue(newItem)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add Favorite")
        }
    }
}

