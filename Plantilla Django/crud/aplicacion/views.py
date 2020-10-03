from django.shortcuts import render, redirect
from .models import *
from django.contrib import messages
from django.contrib.auth import authenticate
from django.contrib.auth.forms import AuthenticationForm
from django.contrib.auth import login as do_login
from django.contrib.auth import logout as do_logout

# Create your views here.

def crearPersona(request):
    if request.session['rol'] is None:
        messages.warning(request,'Por favor inicie sesion')
        return redirect('index')
    if request.method == 'POST':
        pe = Persona()
        pe.nombre = request.POST['nom']
        pe.apellidos = request.POST['ape']
        pe.edad = 12
        pe.telefono = request.POST['te']
        try:
            pe.save()
            messages.success(request,'Persona registrada')
        except:
            messages.warning(request,'Error al registrar')
        return redirect('listar_persona')
    return render(request,'crear_persona.html',{})


def listarPersona(request):
    if request.session['rol'] is None:
        messages.warning(request,'Por favor inicie sesion')
        return redirect('index')
    if request.method == 'POST' and request.POST['ide']:
        persona = Persona.objects.filter(id = request.POST['ide'])
    else:    
        persona = Persona.objects.all()
    context = {'persona':persona}
    return render(request,'listar_persona.html',context)

def editarPersona(request,id):
    if request.session['rol'] is None:
        messages.warning(request,'Por favor inicie sesion')
        return redirect('index')
    pe = Persona.objects.get(id = id)
    if request.method == 'GET':
        context = {'persona':pe}
        return render(request, 'modificar_persona.html',context)
    else:
        pe.nombre = request.POST['nom']
        pe.apellidos = request.POST['ape']
        pe.edad = 17
        pe.telefono = request.POST['te']
        try:
            pe.save()
            messages.success(request,'Persona modificada')
        except:
            messages.warning(request,'Error al modificar')
    return redirect('listar_persona')

def eliminarPersona(request,id):
    if request.session['rol'] is None:
        messages.warning(request,'Por favor inicie sesion')
        return redirect('index')
    pe = Persona.objects.get(id = id)
    try:
        pe.delete()
        messages.success(request,'Persona eliminada')
    except:
        messages.warning(request,'Error al eliminar')
    return redirect('listar_persona')

def login(request):
    if request.method == "POST":
        username = request.POST['usuario']
        password = request.POST['contrasena']
        try:
            persona = Persona.objects.get(nombre = username, id = password)
            request.session['rol'] = 'holi'
            return redirect('/listar_persona')
        except:
            messages.warning(request,'Usuario o contraseña incorrectos')
    return render(request, "index.html",{})

def logout(request):
    request.session['rol'] = None
    return redirect('index')