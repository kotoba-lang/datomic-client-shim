;; nbb test runner — first-class runtime per repo rule (kotoba wasm >
;; clojurewasm > cljs > nbb > (jvm/bb)). Run from the repo root:
;;
;;   nbb --classpath "src:test:.deps/kotobase/src:.deps/kotobase-query/src:.deps/arrangement/src:.deps/prolly-tree/src:.deps/io-ipld/src:.deps/io-multiformats/src:.deps/org-ietf-cbor/src" bin/run_tests.cljs
;;
;; where every .deps/<name> is a checkout of the matching kotoba-lang repo
;; at the SHA pinned in deps.edn (kotobase, kotobase-query) or in
;; kotobase-query's own deps.edn transitively (arrangement, and
;; arrangement's own deps.edn transitively: prolly-tree, io-ipld,
;; io-multiformats, org-ietf-cbor). CI pins every one of them to the same
;; SHAs -- see .github/workflows/ci.yml.
;;
;; arrangement/prolly-tree/io-ipld depend on the npm package @noble/hashes
;; -- `npm install` this repo's package.json before running (see README).
(ns run-tests
  (:require [cljs.test :as t]
            [kotobase.protocols.datomic-test]))

(defmethod t/report [:cljs.test/default :end-run-tests] [m]
  (when-not (t/successful? m)
    (set! (.-exitCode js/process) 1)))

(t/run-tests 'kotobase.protocols.datomic-test)
