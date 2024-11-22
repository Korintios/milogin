# MiLogin  
Plugin de autenticación para Minecraft, desarrollado como parte de una **prueba técnica**. Diseñado para ofrecer máxima seguridad, registro detallado y una integración avanzada con herramientas externas, **MiLogin** es la solución ideal para servidores que buscan proteger a sus jugadores mientras facilitan la administración.

## ✨ Características  

### 🔐 **Sistema de Logueo**  
- Asegura que solo los jugadores registrados puedan acceder al servidor.  
- Autenticación mediante contraseñas encriptadas para mayor seguridad.  

### 📝 **Sistema de Registro**  
- Permite a los nuevos jugadores crear sus cuentas directamente en el juego.  

### 🗄️ **Servicio de Base de Datos**  
- Compatible con **SQLite** para almacenar y gestionar los datos de los jugadores.  
- Estructura optimizada para garantizar rendimiento y seguridad.  

### 🔔 **Integración con Discord Webhooks**  
- Notificaciones automáticas en tu canal a eleccion de discord para eventos importantes como:  
- Inicios de sesión.
- Salida de los jugadores.

### 📊 **Logs de Entradas y Salidas**  
- Almacena un historial detallado de las conexiones y desconexiones de los jugadores en la base de datos.  
- Ideal para rastrear actividad y analizar el comportamiento de los usuarios.  

## 🛠️ Herramientas Requeridas  

Para desarrollar y personalizar este plugin, se recomienda utilizar:  
- **IntelliJ IDEA** (recomendado por su excelente manejo de dependencias y herramientas).  
- **Eclipse IDE** (opcional).  

> **Nota:** Si usas **Eclipse IDE**, asegúrate de que tu configuración soporta Maven y las librerías necesarias.  

## 📦 Librerías Necesarias  

El proyecto utiliza las siguientes dependencias, que deben incluirse en el archivo `pom.xml` si estás trabajando con Maven:  

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.12.0</version>
    </dependency>
    <dependency>
        <groupId>com.konghq</groupId>
        <artifactId>unirest-java</artifactId>
        <version>3.13.6</version>
    </dependency>
    <dependency>
        <groupId>org.mindrot</groupId>
        <artifactId>jbcrypt</artifactId>
        <version>0.4</version>
    </dependency>
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.42.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.spigotmc</groupId>
        <artifactId>spigot-api</artifactId>
        <version>1.20.1-R0.1-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

---

## 🚀 Instalación  

### Paso 1: Clonar el Repositorio  
Este proyecto es de código abierto, lo que significa que puedes descargarlo, personalizarlo y adaptarlo según tus necesidades. Para comenzar:  

1. Abre tu terminal o línea de comandos.  
2. Clona el repositorio en tu máquina local utilizando el siguiente comando:  

   ```bash
   git clone https://github.com/tu-usuario/milogin.git
   ```  

3. Navega al directorio del proyecto:  

   ```bash
   cd milogin
   ```  

---

### Paso 2: Importar el Proyecto en IntelliJ IDEA  
1. Abre **IntelliJ IDEA**.  
2. Selecciona la opción **"Open"** y busca la carpeta donde clonaste el repositorio `milogin`.  
3. IntelliJ detectará automáticamente que es un proyecto basado en Maven y descargará las dependencias necesarias. Si no lo hace:  
   - Ve al menú **View → Tool Windows → Maven** y selecciona **Reload All Maven Projects**.  
4. Configura el **JDK** para el proyecto (se recomienda Java 17 para compatibilidad con Spigot 1.20.1):  
   - Ve a **File → Project Structure → SDK** y selecciona tu instalación de Java 17.  

---

### Paso 3: Compilar el Proyecto  
Una vez que el proyecto esté configurado en IntelliJ IDEA:  

1. Ve al menú de Maven en **View → Tool Windows → Maven**.  
2. Selecciona las opciones **Clean** y luego **Install** para realizar una limpieza y construcción del proyecto:  
   - **Clean:** Elimina archivos temporales de compilaciones anteriores.  
   - **Install:** Descarga las dependencias necesarias y compila el proyecto.  

   ![Maven Clean and Install](#)

3. Alternativamente, puedes usar el atajo de terminal dentro de IntelliJ para ejecutar:  

   ```bash
   mvn clean install
   ```  

4. Después de compilar, encontrarás el archivo JAR generado en la carpeta `target`, con un nombre similar a `milogin-1.0-SNAPSHOT.jar`.  

---

### Paso 4: Configuración del Servidor  
1. Copia el archivo JAR generado en la carpeta `plugins` de tu servidor Minecraft.  
2. Inicia el servidor para que el plugin genere automáticamente los archivos de configuración.  
3. Navega a la carpeta `plugins/MiLogin` y edita el archivo `config.yml` para personalizar:  
   - **Conexión a la base de datos** (MySQL o SQLite).  
   - **Discord Webhooks** (opcional).  

---

### Paso 5: Reiniciar el Servidor  
- Después de personalizar el archivo de configuración, reinicia el servidor para aplicar los cambios.  
- Verifica que el plugin se haya cargado correctamente ejecutando el comando `/plugins` en la consola o dentro del juego.  