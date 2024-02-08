(module
(import "runtime" "print" (func $print (type $_sig_i32)))
(import "runtime" "read" (func $read (result i32)))
(import "runtime" "exceptionHandler" (func $exception (type $_sig_i32)))
(type $_sig_i32 (func (param i32)))
(type $_sig_void (func ))
(type $_sig_ri32 (func (result i32)))
(memory 2000)
(global $SP (mut i32) (i32.const 4))
(global $MP (mut i32) (i32.const 0))
(global $CP (mut i32) (i32.const 0))
(global $NP (mut i32) (i32.const 131071996))
(start $globals)
(func $principale_nule(local $temp i32)
(local $localStart i32)

get_global $SP
set_global $MP
get_global $SP
i32.const 8
i32.add
set_global $SP
get_global $MP
set_local $localStart
i32.const 0
get_local $localStart
i32.add
call $read
i32.store
i32.const 4
get_local $localStart
i32.add
call $read
i32.store
i32.const 4
get_local $localStart
i32.add
i32.load
if
i32.const 0
get_local $localStart
i32.add
i32.load
call $print
else
i32.const 0
i32.const 0
get_local $localStart
i32.add
i32.load
i32.sub
call $print
end
)
(func $globals
call $principale_nule
)
(func $reserveStack (param $size i32) (result i32)
get_global $MP
get_global $SP
set_global $MP
get_global $SP
get_local $size
i32.add
set_global $SP
get_global $SP
get_global $NP
i32.gt_u
if
i32.const 3
call $exception
end
)
(func $freeStack (type $_sig_void)
get_global $MP
i32.load offset=8
set_global $CP
get_global $MP
i32.load offset=4
set_global $SP
get_global $MP
i32.load
set_global $MP
)
(export "principale" (func $principale_nule))
)