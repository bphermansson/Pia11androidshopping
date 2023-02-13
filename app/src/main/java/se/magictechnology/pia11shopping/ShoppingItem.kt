package se.magictechnology.pia11shopping

data class ShoppingItem(val shopname : String? = null, val shopamount : Int? = null, var shopdone : Boolean? = null) {
    var fbid : String? = null
}