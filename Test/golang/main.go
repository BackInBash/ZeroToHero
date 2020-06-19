package main

import (
	"fmt"
	"strconv"
)

func ForLoop() {
	// Foreach Loop
	array := []int{1, 2, 4, 8, 16, 32, 64, 128}
	for _, element := range array {
		fmt.Println(strconv.Itoa(element))
	}

	// For Loop
	for i := 0; i < 10; i++ {
		fmt.Println(strconv.Itoa(i))
	}

	// Infinite Loop
	for {
		break
	}
}

func WhileLoop() {
	// No While Loop Lool
}

func switching(input string) {
	// Switch Statement
	switch input {
	case "1":
		println("1")
	case "2":
		fmt.Println(input + " wasd")
		fallthrough
	case "3":
		fmt.Println(input + " lool")
	default:
		println(input + " kek")
	}
}

func main() {
	fmt.Printf("hello, world\n")
	ForLoop()
	switching("2")
	WhileLoop()
}
