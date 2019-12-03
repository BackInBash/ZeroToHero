#include <sqlite3.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <openssl/md5.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>

int create_database(){
    
    sqlite3 *db;
    sqlite3_stmt *res;
    
    int rc = sqlite3_open("index.db", &db);
    
    if (rc != SQLITE_OK) {
        
        fprintf(stderr, "Cannot open database: %s\n", sqlite3_errmsg(db));
        sqlite3_close(db);
        
        return 1;
    }
    
    sqlite3_close(db);
    
    return 0;
}

int initialize_database(){

    sqlite3 *db;
    char *err_msg = 0;
    
    int rc = sqlite3_open("index.db", &db);
    
    if (rc != SQLITE_OK) {
        
        fprintf(stderr, "Cannot open database: %s\n", sqlite3_errmsg(db));
        sqlite3_close(db);
        
        return 1;
    }
    
    char *sql = "DROP TABLE IF EXISTS Files;" 
                "CREATE TABLE Files(Id INTEGER PRIMARY KEY AUTOINCREMENT, FileName TEXT, FilePath TEXT, FileHash TEXT, ScanDate TEXT);";

    rc = sqlite3_exec(db, sql, 0, 0, &err_msg);
    
    if (rc != SQLITE_OK ) {
        
        fprintf(stderr, "SQL error: %s\n", err_msg);
        
        sqlite3_free(err_msg);        
        sqlite3_close(db);
        
        return 1;
    } 
    
    sqlite3_close(db);
    
    return 0;
}

int insert_data(char * FileName, char * FilePath, char * FileHash, sqlite3 *db){
    
    sqlite3_stmt *stmt;  
    int rc;
    char *err_msg = 0;
    
    sqlite3_prepare_v2(db, "INSERT INTO Files(FileName, FilePath, FileHash, ScanDate) VALUES (?1, ?2, ?3, DATETIME('now','localtime'));", -1, &stmt, NULL);

    sqlite3_bind_text(stmt, 1, FileName, -1, SQLITE_STATIC);
    sqlite3_bind_text(stmt, 2, FilePath, -1, SQLITE_STATIC);
    sqlite3_bind_text(stmt, 3, FileHash, -1, SQLITE_STATIC);
    
    rc = sqlite3_step(stmt); 
    
    if (rc != SQLITE_DONE) {
    printf("ERROR inserting data: %s\n", sqlite3_errmsg(db));
    sqlite3_free(err_msg);
    }

    sqlite3_finalize(stmt);

    return 0;
}

char * calculate_md5(const char *filename) {
    unsigned char c[MD5_DIGEST_LENGTH];
    int i;
    MD5_CTX mdContext;
    int bytes;
    unsigned char data[1024];
    char *filemd5 = (char*) malloc(33 *sizeof(char));
 
    FILE *inFile = fopen (filename, "rb");
    if (inFile == NULL) {
        return 0;
    }
 
 
    MD5_Init (&mdContext);
    while ((bytes = fread (data, 1, 1024, inFile)) != 0)
 
    MD5_Update (&mdContext, data, bytes);
    MD5_Final (c,&mdContext);
 
    for(i = 0; i < MD5_DIGEST_LENGTH; i++) {
        sprintf(&filemd5[i*2], "%02x", (unsigned int)c[i]);
    }
 
    fclose (inFile);
    return filemd5;
}

int is_regular_file(const char *path) {
   struct stat statbuf;
   if (stat(path, &statbuf) != 0)
       return 0;

   return S_ISDIR(statbuf.st_mode);
}

void listFilesRecursively(char *basePath, sqlite3 *db)
{
    char path[1000];
    char filepath[1000];
    struct dirent *dp;
    DIR *dir = opendir(basePath);

    // Unable to open directory stream
    if (!dir)
        return;

    while ((dp = readdir(dir)) != NULL)
    {
        if (strcmp(dp->d_name, ".") != 0 && strcmp(dp->d_name, "..") != 0)
        {
            // Construct new path from our base path
            strcpy(path, basePath);
            strcat(path, "/");
            strcat(path, dp->d_name);

            if(is_regular_file(path) != 1){

                strcpy(filepath, basePath);
                strcat(filepath, "/");
                strcat(filepath, dp->d_name);

                insert_data(dp->d_name, filepath, calculate_md5(filepath), db);              
            }

            listFilesRecursively(path, db);
        }
    }
    
    closedir(dir);
}

int main(int argc,char* argv[]) 
{ 
    char * path;
    int counter; 
    
    if(argc==1){
        printf("\n FileIndexer:\n\n");
        printf("Usage: \n  --path   Path to Index\n\n");
        return 1;
    }
    
    if(argc>=2) 
    { 
        for(counter=0;counter<argc;counter++) 
           if (strstr(argv[counter], "--path") != NULL) {
               size_t len_str = strlen(argv[counter+1]) + 1;
               path = malloc(len_str);
               strncpy(path, argv[counter+1], len_str);
           }
    } 
    if( access( "index.db", F_OK ) == -1 ) {
    // file exists  
        int create = create_database();
        if(create == 1){
            return 1;
        }
    
    
        int init = initialize_database();
        if(init == 1){
            return 1;
        }
    }

    sqlite3 *db;
    int rc = sqlite3_open("index.db", &db);
    
    if (rc != SQLITE_OK) {
        fprintf(stderr, "Cannot open database: %s\n", sqlite3_errmsg(db));
        sqlite3_close(db);
    }

    listFilesRecursively(path, db);

    sqlite3_close(db);

    return 0; 
}