package cn.wufuqi.recyclerviewbindingadapter.utils



fun <T : Any> T.toTypeString() = this::class.java.name + " data：\n${typeToString(this, 0)}"

private fun typeToString(data: Any?, count: Int): String {
    if (data == null) {
        return "null"
    }
    var tableStr = ""
    var tableEndStr = ""
    for (i in 0..count) {
        if (i != 0) {
            tableEndStr += "\t"
        }
        tableStr += "\t"
    }

    var strData = ""
    if (data is String || data is Byte || data is Map<*, *>
        || data is Int || data is Boolean || data is Float
        || data is Double || data is Long || data is Short
    ) {
        strData = data.toString()

        if (data is String) {
            strData = "\"" + strData + "\""
        } else if (data is Float) {
            strData += "F"
        } else if (data is Long) {
            strData += "L"
        } else if (data is Short) {
            strData += "S"
        }
    } else if (data is List<*>) {

        strData = "[@" + data::class.java.name
        for (i in data.indices) {
            var comma = ""
            if (i != data.size - 1) {
                comma = ","
            }

            strData += "\n" + tableStr + typeToString(data[i], count + 1) + comma
        }
        strData += "\n$tableEndStr]"
    } else if (data is Array<*>) {
        strData = "[@" + data::class.java.name
        for (i in data.indices) {
            var comma = ""
            if (i != data.size - 1) {
                comma = ","
            }

            strData += "\n" + tableStr + typeToString(data[i], count + 1) + comma
        }
        strData += "\n$tableEndStr]"
    } else if (data is Set<*>) {
        strData = "{@" + data::class.java.name
        var i = 0
        for (item in data) {
            i++
            var comma = ""
            if (i != data.size - 1) {
                comma = ","
            }
            strData += "\n" + tableStr + typeToString(item, count + 1) + comma
        }
        strData += "\n$tableEndStr}"
    } else {
        strData = "{"
        val c1: Class<*> = data::class.java
        for (f in c1.declaredFields) {
            f.isAccessible = true
            val name = f.name
            val value = f.get(data)
            strData += "\n" + tableStr + name + " = " + typeToString(value, count + 1)
        }
        strData += "\n$tableEndStr}"
    }
    return strData
}