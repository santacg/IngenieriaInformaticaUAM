from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .api import ChessGameViewSet

# Definimos el router
router = DefaultRouter()
router.register(r'games', ChessGameViewSet)

# Definimos las rutas de la API
urlpatterns = [
    path('', include(router.urls)),
]
