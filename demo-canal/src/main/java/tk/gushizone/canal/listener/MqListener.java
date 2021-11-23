package tk.gushizone.canal.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tk.gushizone.canal.constant.GroupConst;
import tk.gushizone.canal.constant.TopicConst;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/17 3:22 下午
 */
@Slf4j
@Component
public class MqListener {

    /**
     * canal-server 宕机后重启，宕机间的 binlog 不会丢失。
     */
    @KafkaListener(groupId = GroupConst.DEMO_CANAL, topics = TopicConst.DEMO_ITEM)
    public void rec(ConsumerRecord<String, ?> record,
                    Acknowledgment acknowledgment,
                    Consumer<?, ?> consumer) {
        log.info("接收消息: {}", record.value());

        // 手工签收（包含了 addOffset 等）
        acknowledgment.acknowledge();
    }

//=========  insert ==============
//{
//    "data": [
//        {
//            "id": "3",
//            "name": "FooBar",
//            "remark": "remark..."
//        }
//    ],
//    "database": "demo",
//    "es": 1637637497000,
//    "id": 6,
//    "isDdl": false,
//    "mysqlType": {
//        "id": "bigint(20) unsigned",
//        "name": "varchar(50)",
//        "remark": "varchar(255)"
//    },
//    "old": null,
//    "pkNames": [
//        "id"
//    ],
//    "sql": "",
//    "sqlType": {
//        "id": -5,
//        "name": 12,
//        "remark": 12
//    },
//    "table": "item",
//    "ts": 1637637497628,
//    "type": "INSERT"
//}


//=========== update ============
//{
//    "data": [
//        {
//            "id": "3",
//            "name": "FooBar",
//            "remark": "foo bar ..."
//        }
//    ],
//    "database": "demo",
//    "es": 1637637680000,
//    "id": 7,
//    "isDdl": false,
//    "mysqlType": {
//        "id": "bigint(20) unsigned",
//        "name": "varchar(50)",
//        "remark": "varchar(255)"
//    },
//    "old": [
//        {
//            "remark": "remark..."
//        }
//    ],
//    "pkNames": [
//        "id"
//    ],
//    "sql": "",
//    "sqlType": {
//        "id": -5,
//        "name": 12,
//        "remark": 12
//    },
//    "table": "item",
//    "ts": 1637637680559,
//    "type": "UPDATE"
//}


//   =========== delete ============
//{
//    "data": [
//        {
//            "id": "3",
//            "name": "FooBar",
//            "remark": "foo bar ..."
//        }
//    ],
//    "database": "demo",
//    "es": 1637637775000,
//    "id": 8,
//    "isDdl": false,
//    "mysqlType": {
//        "id": "bigint(20) unsigned",
//        "name": "varchar(50)",
//        "remark": "varchar(255)"
//    },
//    "old": null,
//    "pkNames": [
//        "id"
//    ],
//    "sql": "",
//    "sqlType": {
//        "id": -5,
//        "name": 12,
//        "remark": 12
//    },
//    "table": "item",
//    "ts": 1637637775246,
//    "type": "DELETE"
//}


}
