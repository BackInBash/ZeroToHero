# File Scanner
CLI Application to verify a File status across multiple repositories.

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
`mussmatch.json` All Files with hash mismatch (corrupt or missing Files)
