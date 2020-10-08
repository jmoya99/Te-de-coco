"""crud URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
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
from django.urls import path
from django.conf.urls import include, url
from aplicacion.views import *

urlpatterns = [
    path('admin/', admin.site.urls),
    url(r'^crear_persona/',crearPersona,name = "crear_persona"),
    url(r'^$',login,name = "index"),
    url(r'^logout/',logout,name = "logout"),
    url(r'^listar_persona/',listarPersona ,name = "listar_persona"),
    url(r'^modificar_persona/(?P<id>\w+)/$',editarPersona ,name = "editar_persona"),
    url(r'^eliminar_persona/(?P<id>\w+)/$',eliminarPersona ,name = "eliminar_persona"),
]
