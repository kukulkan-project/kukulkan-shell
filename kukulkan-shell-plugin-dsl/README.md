# DSL Project Generator
The Kukulkan plugin for generate a DSL project which includes:
- A Xtext project (generated with the Xtext Project Generator)
- A Theia project
- A Theia extension (language contribution) for IDE

## Getting Started
Build the plugin

    mvn clean install -DskipTests

## Usage
Run the DSL project generator

    dsl-project --name myDsl --basePackage com.company.my --extension myExtension

### Parameters

**Required**

    --name: The DSL name (e.g. MyDSL)
    --extension: The extension associated to the DSL (e.g. myExtension)
    --basePackage: The base package (e.g. com.company.my)

**Optional**

    --version: The version of project (e.g. 0.0.1)
    --description: The description of project (e.g. The DSL for my company)
    --license: The license of project (e.g. MIT)
    --homepage: The homepage of project (e.g www.mycompany.com)
    --theiaVersion: The version of Theia
    --githubUrl: The URL of Github repository
