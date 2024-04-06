"""
URL configuration for mychess project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include
from rest_framework.routers import DefaultRouter
from models.api import MyTokenCreateView, ChessGameViewSet  # Asegúrate de importar correctamente

# Crear el router y registrar nuestros viewsets
router = DefaultRouter()
router.register(r'games', ChessGameViewSet)

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/v1/', include('djoser.urls')),
    path('api/v1/', include('djoser.urls.authtoken')),
    path('api/v1/mytokenlogin/', MyTokenCreateView.as_view()),

    # Incluir las URLs de router para el ChessGameViewSet
    path('api/v1/', include(router.urls)),  # Asegúrate de que esta línea esté al final
]
