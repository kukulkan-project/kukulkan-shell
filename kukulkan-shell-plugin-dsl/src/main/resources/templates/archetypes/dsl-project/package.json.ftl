{
  "private": true,
  "name": "${project.name}-ide",
  "productName": "${project.name}-ide",
  "description": "The IDE for ${project.name} DSL",
  "main": "src-gen/frontend/electron-main.js",
  "version": "${project.version}",
  "author": {
    "name": "kukulkan",
    "email": "kukulkan@kukulkan.com"
  },
  <#if project.githubUrl?has_content>
  "repository": {
    "type": "git",
    "url": "${project.githubUrl}"
  },
  <#else>
  "repository": {
    "type": "git",
    "url": "https://github.com/kukulkan/${project.name}-ide.git"
  },
  </#if>
  "dependencies": {
    "@theia/core": "${project.theiaVersion}",
    "@theia/filesystem": "${project.theiaVersion}",
    "@theia/git": "${project.theiaVersion}",
    "@theia/workspace": "${project.theiaVersion}",
    "@theia/preferences": "${project.theiaVersion}",
    "@theia/navigator": "${project.theiaVersion}",
    "@theia/process": "${project.theiaVersion}",
    "@theia/terminal": "${project.theiaVersion}",
    "@theia/editor": "${project.theiaVersion}",
    "@theia/languages": "${project.theiaVersion}",
    "@theia/markers": "${project.theiaVersion}",
    "@theia/monaco": "${project.theiaVersion}",
    "@theia/typescript": "${project.theiaVersion}",
    "@theia/messages": "${project.theiaVersion}",
    "${project.name}-extension": "${project.version}"
  },
  "devDependencies": {
    "@theia/cli": "${project.theiaVersion}",
    "electron-builder": "^20.4.1",
    "electron-rebuild": "1.7.3",
    "lerna": "^2.2.0"
  },
  "scripts": {
    "prepare": "theia build",    
    "start": "theia start",
    "watch": "theia build --watch",
    "package": "ELECTRON_BUILDER_ALLOW_UNRESOLVED_DEPENDENCIES=true electron-builder",
    "rebuild": "electron-rebuild"
  },
  "theia": {
    "target": "electron"
  },
  "workspaces": [
    "${project.name}-extension"
  ]
}