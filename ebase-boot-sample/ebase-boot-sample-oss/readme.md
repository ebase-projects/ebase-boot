```shell script
 docker restart minio
 
 nohup java -server -jar  ebase-boot-sample-oss-1.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
 
 docker run -p 9000:9000 -e MINIO_ACCESS_KEY=admin \
                         -e MINIO_SECRET_KEY=admin123456 \
                         --name minio \
                         -d \
                         -v /mnt/data:/data minio/minio server /data



curl --location --request POST 'http://localhost:18080/upload/uploadVideo' \
--form 'file=@/Users/liudw/Movies/Spring Security开发安全的REST服务/1-1 导学.mp4'
```
