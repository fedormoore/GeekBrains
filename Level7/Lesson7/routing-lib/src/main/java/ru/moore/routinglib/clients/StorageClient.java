package ru.moore.routinglib.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("ms-storage")
public interface StorageClient {
}
