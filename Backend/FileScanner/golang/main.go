package main

import (
	"crypto/md5"
	"encoding/hex"
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"os"
	"path/filepath"
)

// Cli Args Object
type cliArgs struct {
	path  string
	check bool
}

// JFile Object
type JFile struct {
	path string
	hash string
}

// Calculate MD5 FIle Hash
func calculatemd5(filePath string) (string, error) {
	//Initialize variable returnMD5String now in case an error has to be returned
	var returnMD5String string

	//Open the passed argument and check for any error
	file, err := os.Open(filePath)
	if err != nil {
		return returnMD5String, err
	}

	//Tell the program to call the following function when the current function returns
	defer file.Close()

	//Open a new hash interface to write to
	hash := md5.New()

	//Copy the file in the hash interface and check for any error
	if _, err := io.Copy(hash, file); err != nil {
		return returnMD5String, err
	}

	//Get the 16 bytes hash
	hashInBytes := hash.Sum(nil)[:16]

	//Convert the bytes to a string
	returnMD5String = hex.EncodeToString(hashInBytes)

	return returnMD5String, nil

}

func scan(path string) {
	var files []JFile
	err := filepath.Walk(path, func(filepath string, info os.FileInfo, err error) error {
		if info.IsDir() == false {
			md5, err := calculatemd5(filepath)
			if err != nil {
				fmt.Println("MD5 Error: "+filepath, err)
			}
			f := JFile{path: filepath, hash: md5}
			files = append(files, f)
		}
		return nil
	})
	if err != nil {
		panic(err)
	}

	json, _ := json.MarshalIndent(files, "", "    ")
	err = ioutil.WriteFile("output.json", json, os.ModePerm)
	if err != nil {
		panic(err)
	}
	fmt.Println(string(json[:len(json)]))
}

func main() {
	fmt.Println("FileScanner Starting...")
	var cli cliArgs

	// Parse CLI Args
	for index := 0; index < len(os.Args); index++ {
		if os.Args[index] == "-path" {
			cli.path = os.Args[index+1]
		}
		if os.Args[index] == "-c" {
			cli.check = true
		}
	}

	if cli.path == "" {
		fmt.Println("No CLI Parameter: \n -path Path to Folder \n -c Enable Check")
		os.Exit(1)
	}

	// Execute Scan
	if cli.check == false {
		scan(cli.path)
	}
}
