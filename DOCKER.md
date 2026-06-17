# Docker imagem pronta no dockerHub

## Criando uma imagen e push para dockerhub a partir do projeto.

1. **Deletar imagens antigas**
    ```bash
        docker rmi spring3-cassandra:0.1.7
    ``` 
2. **Build do projeto**
    ```bash
   docker build -t spring3-cassandra:0.1.7 -f docker/Dockerfile .   
   ``` 
3. **Tag do build**
   ```bash
        docker tag c964a0be3604 lucasgalo/spring3-cassandra:0.1.7
   ```
4. **Login dockerhub**
   ```bash
        docker login
   ```
5. **Push da tag**
    ```bash
        docker push lucasgalo/spring3-cassandra:0.1.7
    ```
