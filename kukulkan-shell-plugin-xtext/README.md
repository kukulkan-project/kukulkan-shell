# Xtext Project Generator
This is a Kukulkan plugin for generate an Xtext project which includes:
- An `xtext` file to define the grammar
- The `org.xtext.xtend` plugin for gradle
- The `shadowJar` plugin to package the Language Server into a `jar`

## Getting Started
Build the plugin

  mvn clean install -DskipTests

## Usage
Run the Xtext project generator

    xtext-project --extension myExtension --basePackage com.company.my --name myDsl

### Options

**Required**


    -- name: The DSL name (e.g. MyDSL)
    -- extension: The extension associated to the DSL (e.g. myExtension)
    -- basePackage: The base package (e.g. com.company.my)


**Optional**


    -- version: The version of project (e.g. 0.0.1)
    -- description: The description of project (e.g. The DSL for my company)
    -- license: The license of project (e.g. MIT)
    -- homepage: The homepage of project (e.g www.mycompany.com)
