import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ShoppingItem(var name: String, var quantity: Int)

@Composable
@Preview(showBackground = true)
fun ShoppingListApp() {
    var shoppingList by remember {
        mutableStateOf(
            listOf<ShoppingItem>(
                ShoppingItem(
                    "Orange",
                    12
                )
            )
        )
    }

    var showModal by remember {
        mutableStateOf(false)
    }
    var editItemIdx by remember {
        mutableIntStateOf(-1)
    }
    var itemName by remember {
        mutableStateOf("")
    }
    var itemQuantity by remember {
        mutableIntStateOf(0)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = {
            showModal = true
            itemName = ""
            itemQuantity = 0
            editItemIdx = -1
        }) {
            Text(text = "Add Item")
        }
        LazyColumn {
            itemsIndexed(shoppingList) { idx, item ->
                ShoppingItemRow(
                    item = item,
                    onEdit = {
                        editItemIdx = idx
                        itemName = item.name
                        itemQuantity = item.quantity
                        showModal = true
                    },
                    onDelete = {
                        val newList = shoppingList.toMutableList()
                        newList.removeAt(idx)
                        shoppingList = newList
                    })
            }
        }
    }

    if (showModal) {
        AlertDialog(
            onDismissRequest = { showModal = false },
            title = { Text(text = if (editItemIdx == -1) "Add Shopping Item" else "Edit Shopping Item") },
            text = {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Name:", modifier = Modifier.fillMaxWidth(0.3f))
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = { itemName = it },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Quantity:", modifier = Modifier.fillMaxWidth(0.3f))
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(50.dp),
                            value = if (itemQuantity == 0) "" else itemQuantity.toString(),
                            onValueChange = { itemQuantity = it.toIntOrNull() ?: 0 }
                        )
                    }
                }
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { showModal = false }) {
                        Text(text = "Cancel")
                    }
                    Button(
                        enabled = itemName.isNotBlank() && itemQuantity > 0,
                        onClick = {
                            // create a new item
                            if (editItemIdx == -1) {
                                shoppingList += ShoppingItem(itemName, itemQuantity)
                            } else {
                                // edit an existing item
                                val newList = shoppingList.toMutableList()
                                newList[editItemIdx] = ShoppingItem(itemName, itemQuantity)
                                shoppingList = newList
                            }
                            showModal = false
                        }) {
                        Text(text = "Confirm")
                    }
                }
            }
        )
    }
}


@Composable
fun ShoppingItemRow(item: ShoppingItem, onEdit: () -> Unit, onDelete: () -> Unit) {
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .padding(16.dp, 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.name, modifier = Modifier.fillMaxWidth(0.55f))
        Text(text = item.quantity.toString(), modifier = Modifier.weight(0.15f))
        IconButton(onClick = { onEdit() }, modifier = Modifier.weight(0.15f)) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Shopping Item")
        }
        IconButton(onClick = { onDelete() }, modifier = Modifier.weight(0.15f)) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Check Shopping Item")
        }
    }
}