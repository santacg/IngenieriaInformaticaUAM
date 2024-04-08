from django.urls import re_path
from .consumers import GameConsumer

websocket_urlpatterns = [
    re_path(r'ws/play/(?P<gameID>\w+)/token/(?P<token>\w+)/$', GameConsumer.as_asgi()),
]