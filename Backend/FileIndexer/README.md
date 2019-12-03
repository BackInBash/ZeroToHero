# File Indexer

Indexing Files into a SQlite Database.

## Supported OS
+ Ubuntu 19.04

## Usage

```bash
./FileIndexer --path /mnt/
```
## Requirements
+ libssl-dev
+ sqlite3-dev
+ build-essential

## Build instructions
Build with `gcc`

```bash
gcc main.c -l sqlite3 -fno-stack-protector -lssl -lcrypto
```