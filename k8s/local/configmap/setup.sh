#!/usr/bin/env bash
kubectl delete configmap logstash-pipelines
kubectl delete configmap logstash-config
kubectl delete configmap nginx-web
kubectl delete configmap nginx-proxy

kubectl create configmap logstash-config --from-file=./logstash/config
kubectl create configmap logstash-pipelines --from-file=./logstash/pipelines
kubectl create configmap nginx-web --from-file=./nginx/web/nginx.conf
kubectl create configmap nginx-proxy --from-file=./nginx/proxy/nginx.conf
