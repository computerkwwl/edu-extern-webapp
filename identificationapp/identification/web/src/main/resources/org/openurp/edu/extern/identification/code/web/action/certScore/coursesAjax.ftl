[#ftl]
{
  "courses": {
    [#list courses as course]
    "${course.id}": "${course.name}(${course.code})"[#if course_has_next],[/#if]
    [/#list]
  }
}