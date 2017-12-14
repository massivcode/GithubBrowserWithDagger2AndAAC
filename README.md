# 깃허브 브라우저 샘플 앱 (작성중)

간단한 기능을 수행할 수 있는 깃허브 브라우저 앱

## 1. 개발환경

* IDE : Android Studio 3.0.1
* Language : Java

### 사용 라이브러리

* [Retrofit2](https://github.com/square/retrofit) : 통신 관련 라이브러리.
* [ButterKnife](https://github.com/JakeWharton/butterknife) : View DI 라이브러리.
* [Dagger2](https://github.com/google/dagger) : DI 라이브러리.
* [Realm Java](https://github.com/realm/realm-java) : 모바일 DB 라이브러리.
* [Glide](https://github.com/bumptech/glide) : 비동기 이미지 로더.
* [Android Architecture Components (Lifecycle Extensions)]()
* [OkHttp Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) : 통신 디버그 로거
* [github-colors](https://github.com/ozh/github-colors) : Github 에서 사용되는 언어색상 에셋
* [MarkdownView-Android](https://github.com/mukeshsolanki/MarkdownView-Android) : 마크다운 뷰

## 2. 지원기능

- [x] 사용자 프로필 조회
- [ ] 사용자 gist 조회
- [ ] 사용자 gist 내용 출력
- [ ] 사용자 gist 댓글 출력
- [X] 사용자 팔로워, 팔로잉 조회
- [x] 저장소 조회
- [ ] 저장소 파일 탐색 / 파일 내용 출력
- [ ] Dagger2 적용