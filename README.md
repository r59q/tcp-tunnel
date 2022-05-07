# tcp-tunnel handin 9

## Testing

#### Step 1 Running the tunnel
To run the tunnel use the syntax
```
> java src/ServerTunnel.java <Listen port> <Destination host name> <Destination port>
```

#### Step 2 Running a echo server
The echo server should just return whatever you sent it.
To run the echo server use syntax
```
> java src/EchoServer.java <Listen port>
```


#### Step 3 Connect to tunnel
Now use netcat or nc

```
> ncat -v <Server tunnel host name> <Server tunnel port>
```
or 

```
> nc -v <Server tunnel host name> <Server tunnel port>
```
