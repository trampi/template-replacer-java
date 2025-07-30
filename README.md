# template replacer

## Usage

First, create a `replacement.yml` file with the following format:

```yaml
- source: '/tmp/source'
  replacements:
    - from: "example"
      line: 1
      to: "replacement"
    - from: "hello world"
      to: "welcome everyone"
  target:
    - path: "/tmp/1"
      replacements:
        - from: "day"
          to: "night"
    - path: "/tmp/2"
```

with `/tmp/source` containing the following text

```text
example
example
this line contains "hello world" as a text
day
```


Then, run the following command:

```bash
java -jar template-replacer.jar replacement.yml
```

This should result in the creation of two files, `/tmp/1` and `/tmp/2`.

`/tmp/1` should contain the following text:

```text
replacement
example
this line contains "welcome everyone" as a text
day
```

`/tmp/2` should contain the following text:
```text
replacement
example
this line contains "welcome everyone" as a text
night
```

## How it works

For each line of the source file, we check if any of the replacements matches by searching for the `from` String.
Replacements are evaluated in-order.
Replacements specified in `target` are being evaluated before the `replacements` specified in the root.
If a replacement matches, we replace the match with the replacement's `to` value.
If no rule matches, we copy the line as-is.
