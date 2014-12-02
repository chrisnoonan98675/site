---
title: Configuring metrics to an XL Satellite server
subject:
- Satellite
categories:
- xl-satellite
tags:
- metrics
---

## Configuring the hostname and port for metrics interface

XL-Satellite server binds an HTTP server to serves as JSON useful metrics. You can check streaming activities, number of tasks running and JVM technical metrics. 

By default, server binds to every interface, that is to say '0.0.0.0'. You can change this property to specify a hostname or an IP which will be bound by this HTTP server. 

    satellite {
        metrics {
            hostname="0.0.0.0"
        }
    }

You can also change the default port, which is '8080' by default.
  
    satellite {
        metrics {
            port = 8080
        }
    }
    
## Metrics
Once configured, you can access to XL-Satellite metrics given the configured hostname and ip. 

###Registered plugins

    http://${satellite.metrics.hostname}:${satellite.metrics.port}/application
    
    {"plugins": [{"webserver-plugin" : "5.0.0"}]}

###Technical metrics

    http://${satellite.metrics.hostname}:${satellite.metrics.port}/metrics
    
    {
        version: "3.0.0"
        gauges: {
            streaming.server-status: {
                value: "/0:0:0:0:0:0:0:0:8480"
            }
            task.started: {...}
        }
        
        counters: {
            task.done: {
                count: 0
            }
            task.running: {...}
        }
        
        meters: {
            streaming.uploaded-bytes: {
                count: 0
                m15_rate: 0
                m1_rate: 0
                m5_rate: 0
                mean_rate: 0
                units: "events/second"
            }
        }
        timers: {
            streaming.transferred-tasks: {
                count: 0
                max: 0
                mean: 0
                min: 0
                p50: 0
                p75: 0
                p95: 0
                p98: 0
                p99: 0
                p999: 0
                stddev: 0
                m15_rate: 0
                m1_rate: 0
                m5_rate: 0
                mean_rate: 0
                duration_units: "milliseconds"
                rate_units: "calls/second"
            }
            task.execution: {...}
        }
    }

###Configuration

    http://${satellite.metrics.hostname}:${satellite.metrics.port}/configuration
    
    {
        akka: {...}
        deployit: {...}
        java: {...}
        line: {...}
        os: {...}
        path: {...}
        satellite: {
            metrics: {
                hostname: "0.0.0.0"
                port: 8080
            }
            streaming: {
                chunk-size: 100000
                compression: "off"
                port: 8480
            }
            timeout: {
                upload: {
                    idle: "5 seconds"
                }
            }
            workdir: "/tmp/var/satellite"
        }
        spray: {...}
        sun: {...}
        user: {...}
    }    