--- https://www.runoob.com/lua/lua-tutorial.html
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by gushi.
--- DateTime: 2021/8/12 11:21 上午

--- 限流标识
local key = KEYS[1]
redis.log(redis.LOG_DEBUG, 'key is ..', key)

--- 限流阈值
local threshold = tonumber(ARGV[1])
--- 当前流量大小
local count = tonumber(redis.call('get', key) or "0")

if count + 1 > threshold then
    return false
else
    redis.call("incrby", key, 1)
    redis.call("expire", key, ARGV[2])
    return true
end

