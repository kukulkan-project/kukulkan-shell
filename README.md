<h1 align="center">Bienvenido a Kukulkan 👋</h1>
<p>
  <a href="https://www.npmjs.com/package/generator-crip" target="_blank">
    <img alt="Version" src="https://img.shields.io/npm/v/generator-crip.svg">
  </a>
  <a href="#" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
</p>

# Tabla de Contenido

1. [Introducción](#introducción)
2. [Descripción de un componente](#descripción-de-un-componente)
3. [Arquitectura del Generador](#arquitectura-del-generador)
    1. [Arquitectura Lógica de un generador](#arquitectura-lógica-de-un-generador)
        1. [Modelo Lógico del Generador](#modelo-lógico-del-generador)
        2. [Modelo de Plantillas del Generador](#modelo-de-plantillas-del-generador)
    2. [Arquitectura Física del generador](#arquitectura-física-del-generador)
        1. [Descripción de la capa comandos](#descripción-de-la-capa-comandos)
        2. [Descripción de la capa config](#descripción-de-la-capa-config)
        3. [Descripción de la capa generator](#descripción-de-la-capa-generator)
        4. [Descripción de la capa util](#descripción-de-la-capa-util)
        5. [Descripción de la capa template](#descripción-de-la-capa-template)

# Introducción

En esta sección se describen los principales elementos que conforman a un generador, utilizando la arquitectura de implementación kukulkan.

# Descripción de un componente

Antes de comenzar, se describe la nomenclatura utilizada para el nombrado de los diferentes componentes que se utilizan en la presente arquitectura de referencia, así como también se mostrarán los usos comunes y sus relaciones.

Cada nombre, referenciado aquí, está compuesto de dos partes a saber:

<b>\<Name\></b> <b>\<LayerComponent\></b>
 
en donde:

<b>\<Name\></b>: es el nombre del componente que se desea instanciar y su definición dependerá del diseñador e implementador del componente.

<b>\<LayerComponent\></b>: es el nombre que hace referencia a los componentes que conforman a una capa arquitectónica o subsistema. Este elemento no se puede modificar, se encuentra predefinido y es un estándar utilizado a lo largo de todo el desarrollo de componente. En cada capa se tendrá su propio nombrado y deberá de respetarse.

En lo que resta, nos enfocaremos en la definición de cada capa del componente, los elementos que la constituyen y los lineamientos a los que nos tendremos que apegarnos. Por simplicidad, se omite la extensión de los archivos a utilizar.

# Arquitectura del Generador

La arquitectura de un generador, estará dividida en dos niveles de representación de la misma: arquitectura lógica y arquitectura física. En lo que resta, nos vamos a enfocar en la descripción detallada de cada una de estas representaciones.

## Arquitectura Lógica de un generador

En esta vista, se exponen dos modelos para la representación de la arquitectura lógica del generador: **modelo lógico del generador** y **modelo de plantillas del generador**. Para ambas representaciones, se utilizará un diagrama de paquetes y clases, en el que se representarán los principales componentes que conforman al componente. La separación de la representación en dos modelos, es necesaria para enfatizar el modelo de plantillas que utiliza un generador y que está, se encuentra separa de la lógica que utiliza dichas plantillas.

### Modelo Lógico del Generador

La estructura del código para el generador, se representa en la siguiente imagen:
/plugin-resources.png
<img src="https://raw.githubusercontent.com/kukulkan-project/resources/master/plugin-source-code.png" width="500" height="500" title="Código del generador">

### Modelo de Plantillas del Generador

La estructura en dónde se guardan las plantillas de un generador, muestra en la siguiente imagen: 
 
![login-model](https://raw.githubusercontent.com/kukulkan-project/resources/master/plugin-resources.png)

### Arquitectura Física del generador

La arquitectura física del generador, se representa con una vista de carpetas, misma que representa la estructura de un proyecto estándar:

```
├───.mvn
│   └───wrapper
└───src
    ├───main
    │   ├───java
    │   │   └───mx
    │   │       └───infotec
    │   │           └───dads
    │   │               └───kukulkan
    │   │                   └───shell
    │   │                       ├───commands
    │   │                       ├───config
    │   │                       ├───generator
    │   │                       ├───template
    │   │                       └───util
    │   └───resources
    │       └───templates
    │           └───<templateNameRoot>
    └───test
        └───resources
```

A continuación se describe a cada una de las capas antes expuestas.

## Descripción de la capa comandos

La capa comandos contiene la implementación de los comandos que se instrumentarán en el componente y qué serán utilizados a través de la shell de kukulkan. Kukulkan está inspirado en el marco de trabajo de Spring Shell. Para mayor información sobre la implementación de un comando, se puede consultar la documentación de Spring Shell(https://docs.spring.io/spring-shell/docs/current-SNAPSHOT/reference/htmlsingle).

Los principales elementos que están contenidos en esta capa son los siguientes:

1. <b>\<Name\></b>Command 
2. <b>\<Name\></b>Args

El componente \<**Name**\>Command contiene toda la implementación de un comando y los argumentos que éste recibe están definidos y encapsulados dentro del componente <b>\<Name\></b>Args.
 
A continuación, se describen los lineamientos a los que está sujeta esta capa:
Lineamientos para el componente <b>\<Name\></b>Command:
 
1. deberá de ser decorada a través de la anotación `@ShellComponent`
2. deberá de extender a la clase `mx.infotec.dads.kukulkan.shell.commands.AbstractCommand`
3. deberá de tener una propiedad que le permita generar mensajes de log y deberá de ser de la siguiente manera: private static final Logger LOGGER = LoggerFactory.getLogger(<b><Name></b>Command.class);
4. deberá de utilizar la clase “<b>\<Name\></b>Generator” a través de la anotación de `@autowired`.
5. deberá de tener al menos un método que implemente un comando.
6. en caso de utilizar el componente <b><Name></b>Args, deberá der utilizado en el contexto de la definición del método.

Lineamientos para el componente <b>\<Name\></b>Args: 

1. deberá de implementar a la interfaz “_Serializable_”.
2. deberá de tener al menos la siguiente propiedad: 

```java
@Parameter
private List<String> parameters = new ArrayList<>();
```
3. deberá de sobre escribir el método `toString()`, de tal manera que regrese información sobre los parámetros que éste contiene.

La capa `commands` contiene la implementación de un comando en el shell, utilizando el api de spring shell

## Descripción de la capa config

En esta capa se deberán de agregar todas las clases de configuración utilizadas por spring-configuration

## Descripción de la capa generator

La capa generator contiene la implementación propia de un generador.

Los principales elementos que están contenidos en esta capa son los siguientes:

1. <b>\<Name\></b>Generator
2. <b>\<Name\></b>Context

A continuación, se describen los lineamientos a los que está sujeta esta capa:

#### Lineamientos para el componente <b>\<Name\></b>Generator: 

1. Deberá de ser decorada a través de la anotación `@GeneratorComponent`
2. Deberá de extender a la clase `mx.infotec.dads.kukulkan.metamodel.generator.Generator`
3. Deberá de tener una propiedad que le permita generar mensajes de log y deberá de ser de la siguiente manera:

```java
private static final Logger LOGGER = LoggerFactory.getLogger(<Name>Generator.class);
```

#### Lineamientos para el componente <b>\<Name\></b>Context: 

1. deberá de extender a la clase `mx.infotec.dads.kukulkan.metamodel.context.BaseContext`.
2. deberá de sobreescribir el método `toString()` que regrese un texto representativo del elemento de contexto.

## Descripción de la capa util

En esta capa se agregarán los componentes con 

## Descripción de la capa template

En esta capa se deberán de agregar todos los componetes que sean necesarios para manejar los archivos template.
