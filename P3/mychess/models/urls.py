from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .api import ChessGameViewSet, MyTokenCreateView

router = DefaultRouter()
router.register(r'games', ChessGameViewSet)

urlpatterns = [
    path('mytokenlogin/', MyTokenCreateView.as_view()),
    path('', include(router.urls)),
]