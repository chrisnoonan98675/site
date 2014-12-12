---
title: Configure XL Deploy satellite metrics
categories:
- xl-deploy
subject:
- Satellite
tags:
- satellite
- system administration
- configuration
---

## Configure host name and port for metrics

An XL Deploy satellite server binds to an HTTP server to provide metrics in JSON format. This enables you to check streaming activities, the number of tasks that are running, and Java virtual machine (JVM) technical metrics.

By default, the HTTP server binds to `0.0.0.0`. To specify a different host name or IP address for the server to bind to, modify the following setting:

    satellite {
        metrics {
            hostname="0.0.0.0"
        }
    }

You can also change the default port, which is `8080` by default.
  
    satellite {
        metrics {
            port = 8080
        }
    }
    
## Metrics

Access the metrics using the host name or IP address and port that you specified in the configuration.

### Registered plugins

    http://${satellite.metrics.hostname}:${satellite.metrics.port}/application
    
    {"plugins": [{"webserver-plugin" : "5.0.0"}]}

### Technical metrics

    http://${satellite.metrics.hostname}:${satellite.metrics.port}/metrics
    
    {
        version: "3.0.0"
        gauges: {
            streaming.server-status: {
                value: "/0:0:0:0:0:0:0:0:8480"
            }
            task.started: {...}
        }

	histograms: {
	    streaming.upload-speed: {
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
	    }

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

### Configuration

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
