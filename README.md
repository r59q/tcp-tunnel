# tcp-tunnel handin 9

## Testing

### Running the tunnel
To run the tunnel use the syntax
```
> java src/ServerTunnel.java <Listen port> <Destination host name> <Destination port>
```

To run the echo server use syntax
```
> java src/EchoServer.java <Listen port>
```

Now use netcat or nc

```
> ncat -v <Server tunnel host name> <Server tunnel port>
```
or 

```
> nc -v <Server tunnel host name> <Server tunnel port>
```
