# File Scanner
CLI Application to verify a File status across multiple repositories.

This Program is avaliable in the following Programming languages:
+ C#
+ Go
+ Java
+ C++ (WIP)
+ Rust (WIP)

## Usage:

Usage as Scanner:
``` console
FileScanner.exe -p C:\Path\To\Index
```

Usage as Comparer:
``` console
FileScanner.exe -p C:\Path\To\Index -c
```

## Output
`output.json` All File status from given Repository

`mismatch.json` All Files with hash mismatch (corrupt or missing Files)
