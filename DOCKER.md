# Docker imagem pronta no dockerHub

## Criando uma imagen e push para dockerhub a partir do projeto.

1. **Deletar imagens antigas**
    ```bash
        docker rmi spring3-cassandra:0.1.3
    ``` 
2. **Build do projeto**
    ```bash
   docker build -t spring3-cassandra:0.1.3 -f docker/Dockerfile .   
   ``` 
3. **Tag do build**
   ```bash
        docker tag 37d21863c5c4 lucasgalo/spring3-cassandra:0.1.3
   ```
4. **Login dockerhub**
   ```bash
        docker login
   ```
5. **Push da tag**
    ```bash
        docker push lucasgalo/spring3-cassandra:0.1.3
    ```
