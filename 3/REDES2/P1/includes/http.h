/**
 * @file http.h
 * 
 * @brief HTTP request handling functions
 * 
 * This header file contains the HTTP request handling functions
 * declarations
 * 
 * @author Carlos Garcia Santa
 */
#ifndef HTTP_H
#define HTTP_H

#include "picohttpparser.h"
#include "server.h"
#include "sockets.h"
#include "utils.h"
#include <sys/types.h>
#include <time.h>

#define MAX_URI_LENGTH 512 // 512 bytes for URI
#define MAX_TIME_STR_LENGTH 32 // 32 bytes for time string

/**
 * @brief Handles an HTTP request
 * 
 * @param thread_data Thread data
 * @param buffer Buffer to store the request
 * @param bytes_received Number of bytes received
 * 
 * @return int 0 if success, -1 otherwise
 */
int handle_http_request(thread_data_t *thread_data, char *buffer,
                        ssize_t bytes_received);

typedef struct {
  const char *extension;
  const char *mime_type;
} mime_map_t;

/**
 * @brief MIME types map
 * 
 * This map contains the MIME types for the supported extensions
 *
 */
static mime_map_t mime_map[] = {{"html", "text/html"},
                                {"txt", "text/plain"},
                                {"jpg", "image/jpeg"},
                                {"jpeg", "image/jpeg"},
                                {"gif", "image/gif"},
                                {"mpeg", "video/mpeg"},
                                {"mpg", "video/mpeg"},
                                {"doc", "application/msword"},
                                {"pdf", "application/pdf"}};

/**
 * @brief HTTP response data structure
 * 
 * This data structure contains the HTTP response data
 * 
 */
typedef struct http_response {
  int protocol_version;
  int status_code;
  char *status_message;
  char *server_name;
  time_t time;
  time_t last_modified;
  off_t content_size;
  const char *content_type;
} http_response_t;

typedef enum {
  HTTP_500_INTERNAL_SERVER_ERROR = 500,
  HTTP_501_METHOD_NOT_IMPLEMENTED = 501,
  HTTP_505_VERSION_NOT_SUPPORTED = 505,
  HTTP_400_BAD_REQUEST = 400,
  HTTP_404_NOT_FOUND = 404,
  HTTP_408_REQUEST_TIMEOUT = 408,
  HTTP_414_Request_URI_Too_Long = 414,
  HTTP_204_NO_CONTENT = 204,
  HTTP_200_OK = 200
} http_status_t;

#endif
