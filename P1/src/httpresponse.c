#include "httpresponse.h"
#include <stdlib.h>
#include <string.h>

typedef struct {
    const char *extension;
    const char *mime_type;
} Mime_dict;

Mime_dict extensions_types [] = {
    {".txt", "text/plain"},
    {".html", "text/html"},
    {".htm", "text/html"},
    {".gif", "image/gif"},
    {".jpeg", "image/jpeg"},
    {".jpg", "image/jpeg"},
    {".mpeg", "video/mpeg"},
    {".mpg", "video/mpeg"},
    {".doc", "application/msword"},
    {".docx", "application/msword"},
    {".pdf", "application/pdf"},
    {NULL, NULL},
};

int process_http_request(int connfd) {
  

  while (1) {
    
  }
}
