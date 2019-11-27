with import <nixpkgs> {};
let
  bash-wrapper = writeScript "shell-wrapper" ''
    #! ${stdenv.shell}
    export PS1="\e[0;34m\u@\h \w $ \e[m"
    exec -a bash ${bashInteractive}/bin/bash --noprofile "$@"
  '';
in stdenv.mkDerivation rec {
  name = "nix-shell";
  buildInputs = [
    jre
    clojure
    leiningen boot
  ];
  shellHook = ''
    export SHELL="${bash-wrapper}"
  '';
}
