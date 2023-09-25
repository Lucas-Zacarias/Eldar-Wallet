
# Wallet

Challenge técnico. App mobile Android


## Installation

Para descargar este proyecto se puede hacer de 2 formas:

```bash
  Descargar el archivo .zip
```
    
```bash
  Hacer un git clone de:
  https://github.com/Lucas-Zacarias/Wallet.git
```    
## Usage
Se debe tener:
* Android Studio para ejecutarlo
* Tener JAVA 17
* Emulador Android con una API no menor a 26
* Una API KEY de https://rapidapi.com/hub
* Se debe crear una clase llamada Secrets en el package: com.eldarwallet.domain.models y debe tener la siguiente estructura


```
package com.wallet.domain.models

class Secrets {

    companion object{
        const val KEY_TO_ENCRYPT = "Colocar un string de 16 caracteres"
        const val RAPID_API_KEY = "Colocar la API KEY generada"
    }

}
```

