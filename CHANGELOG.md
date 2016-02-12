# Change Log
All notable changes to this project will be documented in this file.

## [2.0.0] - 2016-2-12
### Changed
- Do not expose JSON implementation to clients. Use collections instead of
arrays in interfaces. Rename methods where appropriate. This version is not
backwards-compatible with earlier ones.

## [1.2.1] - 2016-2-2
### Changed
- Renamed packages and artifact name. This breaks backwards compatibility.

### Added
- IP pool support

## [1.2.0] - 2015-5-14
### Added
- Support for raw unescaped JSON string - via [#10](https://github.com/sendgrid/smtpapi-java/pull/10)
- Version sanity tests
