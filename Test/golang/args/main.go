package main

import (
	"fmt"
	"os"
	"strconv"
)

type cliArgs struct {
	istrue bool
	path   string
	number int
}

func main() {
	fmt.Println("Go CLI Args App")
	var cli cliArgs

	for index := 0; index < len(os.Args); index++ {
		if os.Args[index] == "-istrue" {
			cli.istrue = true
		}
		switch os.Args[index] {
		case "-path":
			cli.path = os.Args[index+1]
		case "-number":
			i, err := strconv.Atoi(os.Args[index+1])
			if err != nil {
				fmt.Print("Error")
				os.Exit(1)
			}
			cli.number = i
		}
	}
	fmt.Println("Print Output:\n isTrue: " + strconv.FormatBool(cli.istrue) + "\n Path: " + cli.path + "\n Number: " + strconv.Itoa(cli.number))
}
