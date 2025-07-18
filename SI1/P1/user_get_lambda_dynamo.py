#!/bin/env python3

import boto3
import json
import logging
import traceback
import os

LOGGER = logging.getLogger()
LOGGER.setLevel(logging.INFO)

ENDPOINT_URL = f"http://{os.getenv('LOCALSTACK_HOSTNAME')}:{os.getenv('EDGE_PORT')}"

TABLE_NAME = 'si1p1'

def get_dynamodb_resource():
    return boto3.resource(
        "dynamodb",
        aws_access_key_id='test',
        aws_secret_access_key='test',
        region_name='us-east-1',
        endpoint_url=ENDPOINT_URL
    )

DYNAMODB_RESOURCE = get_dynamodb_resource()

def handler(event, context):
    LOGGER.info('Consultando usuario en DynamoDB')
    print("Received event: " + json.dumps(event, indent=2))
    try:
        # Se recupera el nombre de usuario de los parámetros de la ruta
        username = event['pathParameters']['username']
        table = DYNAMODB_RESOURCE.Table(TABLE_NAME)
        
        # Get_item para buscar el usuario en DynamoDB
        response = table.get_item(
            Key={
                'User': username
            }
        )
        
        # Si el usuario se encuentra, se devuelve de lo contrario un objeto vacío
        if 'Item' in response:
            rest_body = response['Item']
        else:
            rest_body = {}

    except Exception:
        print("Error....")
        print(f"error trace {traceback.format_exc()}")
        LOGGER.info(f"error trace {traceback.format_exc()}")
        rest_body = {'msg': f"error trace {traceback.format_exc()}"}
    
    # API GW compliant response
    resp = {
        "isBase64Encoded": False,
        "statusCode": 200,
        "headers": {
            "content-type": "application/json"
        },
        "body": json.dumps(rest_body)
    }
    
    return resp
