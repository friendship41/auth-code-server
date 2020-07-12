# friendship41 인증/인가 서버
포트 : 42222

기능
1. 인증
2. access 토큰 발급
3. refresh 토큰


코드 발급
http://localhost:42222/oauth/authorize?client_id=testApp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_profile
