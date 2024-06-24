/**
 * @file svr_config.h
 * 
 * @brief Server configuration functions header file
 * 
 * This header file contains the server configuration functions
 * declarations
 * 
 * @author Carlos Garcia Santa
 */

#ifndef SVR_CONFIG_H
#define SVR_CONFIG_H

#include "server.h"
#include "utils.h"

/**
 * @brief Sets the server configuration
 *
 * @param server Server configuration
 * @return int 0 if success, -1 otherwise
 */
int server_set_config(server_t *server);

#endif
