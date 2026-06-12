# Docker imagem pronta no dockerHub

## Criando uma imagen e push para dockerhub a partir do projeto.

1. **Deletar imagens antigas**
    ```bash
        docker rmi spring3-cassandra:0.1.6
    ``` 
2. **Build do projeto**
    ```bash
   docker build -t spring3-cassandra:0.1.6 -f docker/Dockerfile .   
   ``` 
3. **Tag do build**
   ```bash
        docker tag d0fa58f51e0e lucasgalo/spring3-cassandra:0.1.6
   ```
4. **Login dockerhub**
   ```bash
        docker login
   ```
5. **Push da tag**
    ```bash
        docker push lucasgalo/spring3-cassandra:0.1.6
    ```
