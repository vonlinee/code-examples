extend type Query{
	#查询
	${table.getEntityPath()}QueryFindAll(pageIndex:Int,pageSize:Int,dataInfo:Input${entity}):ListResult${entity}
}

extend type Mutation{
	#插入
	${table.getEntityPath()}MutationSaveById(dataInfo:Input${entity}):EntityResult${entity}
}

