import service from '@/utils/apiRequest'

/**
 * 可选择的模板列表
 */
export function apiGetSeletableTemplates() {
  return service.get('/api/codegen/template/list/select')
}
