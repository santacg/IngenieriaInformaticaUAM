from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .api import ChessGameViewSet

router = DefaultRouter()
router.register(r'games', ChessGameViewSet)
router.register(r'play', ChessGameViewSet)


urlpatterns = [
<<<<<<< HEAD
=======

>>>>>>> refs/remotes/PSI/master
    path('', include(router.urls)),
]
