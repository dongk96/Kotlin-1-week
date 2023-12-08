package com.example.sparta_kt.FirstWeek
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

const val franchiseKioskName = "SHAKESHACK BURGER"

interface Menu {
    val name: String
    val description: String
}

interface Product : Menu {
    val price: Pair<String, Double>
}

data class MenuItem(override val name: String, override val description: String) : Menu
data class Burger(
    override val name: String,
    override val description: String,
    override val price: Pair<String, Double>
) : Product
data class OrderItem(val burger : Burger, var quantity: Int = 1)



val mainMenu = mutableListOf<MenuItem>()
val orderMenu = mutableListOf<MenuItem>()
val burgerMenu = mutableListOf<Burger>()
val burgerPrices = mutableListOf<Pair<String, Double>>()
val myPocket = mutableListOf<OrderItem>()
val soldAllItem = mutableListOf<OrderItem>()


fun addToOrder(burgerMenu: List<Burger>): OrderItem? {
    burgerCollect(burgerMenu)
    println("원하시는 버거를 선택하세요.")
    val burgerNum = readlnOrNull()?.toIntOrNull()
    var addToCart = false

    if (burgerNum != null && burgerNum in 1..burgerMenu.size) {
        var menuItem = burgerMenu[burgerNum - 1]
        println("${menuItem.name}       | ${menuItem.price.first} ${menuItem.price.second} | ${menuItem.description}")
        while (!addToCart) {
            println("위 메뉴의 어떤 옵션으로 추가하시겠습니까?")
            println("1. Single(${menuItem.price.first} ${menuItem.price.second})   2. Double(${menuItem.price.first} ${menuItem.price.second * 1.2})")
            val sizeOption = readlnOrNull()?.toInt()
            if (sizeOption == 2) {
                menuItem = Burger(menuItem.name + "(Double)", menuItem.description, Pair(menuItem.price.first, menuItem.price.second * 1.2))
            }
            println()
            println("${menuItem.name}       | ${menuItem.price.first} ${menuItem.price.second} | ${menuItem.description}")
            println("위 메뉴를 장바구니에 추가하시겠습니까?")
            println("1. 확인      2. 취소")
            val option = readlnOrNull()?.toInt()
            if (option == 1) {
                addToCart = true
            }
            else continue
        }
        return OrderItem(menuItem)
    } else {
        println("유효하지 않은 선택입니다. 주문이 추가되지 않았습니다.")
    }

    return null
}


var globalOrderCount = 0

fun placeOrder(orderItems: List<OrderItem>) {
    println("아래와 같이 주문 하시겠습니까?")
    println("[ Order ]")
    var totalPrice = 0.0
    for ((index, orderItem) in orderItems.withIndex()) {
        println("${index + 1}. ${orderItem.burger.name} | ${orderItem.burger.price.first} ${orderItem.burger.price.second} | ${orderItem.burger.description} | ${orderItem.quantity}개")
        totalPrice += (orderItem.burger.price.second * orderItem.quantity)
    }

    println("[ Total ]")
    println("W $totalPrice")

    println("1. 주문      2. 메뉴판")
    val orderNum = readlnOrNull()?.toIntOrNull()

    if (orderNum == 1) {
        println("주문을 처리 중입니다...")
        globalOrderCount++

        println("주문이 완료되었습니다.")

        println("대기번호는 [ $globalOrderCount ] 번 입니다.")
        println("(3초후 메뉴판으로 돌아갑니다.)\n")
        runBlocking { delay(3000) }
    } else {
        println("주문이 취소되었습니다.")
    }
}

fun displayInfo(franchiseKioskName: String) {
    println("\"${franchiseKioskName}에 오신 것을 환영합니다.\"")
    println("아래 메뉴판을 보시고 메뉴를 골라 입력하시오.")
    println()
}

fun mainCollect(mainMenu: List<MenuItem>, orderMenu: List<MenuItem>) {
    displayInfo(franchiseKioskName)
    println("[ SHAKESHACK MENU ]")
    for (i in mainMenu.indices) {
        val menuItem = mainMenu[i]
        println("${i + 1}. ${menuItem.name}        | ${menuItem.description}")
    }
    println()
    println("[ ORDER MENU ]")
    for (i in orderMenu.indices) {
        val orderItem = orderMenu[i]
        println("${i + mainMenu.size + 1}. ${orderItem.name}       | ${orderItem.description}")
    }
}

fun burgerCollect(burgerMenu: List<Burger>) {
    displayInfo(franchiseKioskName)
    for (i in burgerMenu.indices) {
        val burgerItem = burgerMenu[i]
        println("${i + 1}. ${burgerItem.name}  | ${burgerItem.price.first} ${burgerItem.price.second} | ${burgerItem.description}")
    }
}

fun main() {

    mainMenu.add(MenuItem("Burger", "앵거스 비프 통살을 다져만든 버거"))
    mainMenu.add(MenuItem("Frozen Custard", "매장에서 신선하게 만드는 아이스크림"))
    mainMenu.add(MenuItem("Drink", "매장에서 직접 만드는 음료"))
    mainMenu.add(MenuItem("Beer", "뉴욕 브루클린 브루어리에서 양조한 맥주"))

    orderMenu.add(MenuItem("Order", "장바구니를 확인 후 주문합니다."))
    orderMenu.add(MenuItem("Cancel", "진행중인 주문을 취소합니다."))

    burgerPrices.add("W" to 6.9)
    burgerPrices.add("W" to 8.9)
    burgerPrices.add("W" to 9.4)
    burgerPrices.add("W" to 6.9)
    burgerPrices.add("W" to 5.4)

    burgerMenu.add(Burger("Shack Burger", "토마토, 양상추, 쉑소스가 토핑된 치즈버거", burgerPrices[0]))
    burgerMenu.add(Burger("Smoke Burger", "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거", burgerPrices[1]))
    burgerMenu.add(Burger("Shroom Burger", "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거", burgerPrices[2]))
    burgerMenu.add(Burger("Cheese Burger", "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거", burgerPrices[3]))
    burgerMenu.add(Burger("Hamburger", "비프패티를 기반으로 야채가 들어간 기본버거", burgerPrices[4]))


    while (true) {
        mainCollect(mainMenu, orderMenu)
        println("원하시는 항목을 선택하세요.")
        val userInput = readlnOrNull()

        if (userInput.isNullOrBlank()) {
            println("주문을 종료하겠습니다.")
            break
        }

        when (userInput.toInt()) {
            1 -> {
                val orderItem = addToOrder(burgerMenu)
                if (orderItem != null) {
                    val existingItem = myPocket.find { it.burger == orderItem.burger }
                    if (existingItem != null) {
                        existingItem.quantity++
                        println("${orderItem.burger.name}의 수량이 증가되었습니다. 현재 수량: ${existingItem.quantity}\n")
                    } else {
                        myPocket.add(orderItem)
                        println("${orderItem.burger.name}가 장바구니에 추가되었습니다.\n")
                    }
                }
            }

            5 -> {
                if (myPocket.isEmpty()) {
                    println("장바구니가 비어있습니다.")
                    continue
                } else {
                    placeOrder(myPocket)
                    for (pocketItem in myPocket) {
                        if (pocketItem.quantity > 1) {
                            repeat(pocketItem.quantity) {
                                soldAllItem.add(pocketItem)
                            }
                        } else soldAllItem.add(pocketItem)
                    }
                    myPocket.clear()
                }
            }

            6 -> {
                println("진행하던 주문을 취소하시겠습니까?")
                println("1. 확인      2. 취소")
                val orderNum = readlnOrNull()?.toInt() ?: continue

                if (orderNum == 1) {
                    myPocket.clear()
                    println("주문이 취소되었습니다.\n")
                }
            }

            0 -> {
                var allPrices = 0.0
                for (item in soldAllItem) {
                    allPrices += item.burger.price.second
                }
                println("[ 총 판매금액 현황 ]")
                println("현재까지 총 판매된 금액은 [ $allPrices ] 입니다.")
                println()
                println("[ 총 판매상품 목록 현황 ]")
                println("현재까지 총 판매된 상품 목록은 아래와 같습니다.")
                for (soldItem in soldAllItem) {
                    println("- ${soldItem.burger.name}      | ${soldItem.burger.price.first} ${soldItem.burger.price.second}")
                }
                println()
                println("1. 돌아가기")
                val option = readlnOrNull()?.toInt()
                if (option == 1) continue
            }
        }
    }
}


