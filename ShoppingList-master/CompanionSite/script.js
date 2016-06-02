//Connection to Firebase
var firebaseRef = new Firebase("https://shoppingwithstekam.firebaseio.com/");
var productList = document.querySelector("#productsList");
var productsRef = firebaseRef.child("products");

//Adding an item
document.querySelector("#addProduct").addEventListener("click", function(){
  var name = document.querySelector("#productName").value;
  var quantity = parseInt(document.querySelector("#productAmount").value);
  var unit = document.querySelector("#productUnit").value;

  productsRef.push({
    name: name,
    quantity: quantity,
    unit: unit
  });
});

productsRef.on("child_added", function(snapshot){
  var newItem = snapshot.val();
  var listElement = document.createElement("li");
  var checkboxElement = document.createElement("INPUT");
  checkboxElement.setAttribute("type", "checkbox");
  var listTextNode = document.createTextNode(" "+ newItem.quantity + " " + newItem.unit + " " + newItem.name);
  
  listElement.appendChild(checkboxElement);
  listElement.appendChild(listTextNode);
  productList.appendChild(listElement);
  
  //Delete one item
  document.querySelector("#deleteProduct").addEventListener("click", function(){
      if(checkboxElement.checked){
        snapshot.ref().remove();
        productList.removeChild(listElement);
      }
    });

  //Clear all items
  document.querySelector("#clearProducts").addEventListener("click", function(){
    productsRef.remove();
    productList.removeChild(listElement);
  });
});


