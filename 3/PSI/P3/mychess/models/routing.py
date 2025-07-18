from django.urls import re_path
from .consumers import ChessConsumer

# Define the websocket URL patterns
# Autor: Eduardo Junoy Ortega
websocket_urlpatterns = [
    re_path(r'ws/play/(?P<gameID>\d+)/token/(?P<token>\w+)/$',
            ChessConsumer.as_asgi()),
]
