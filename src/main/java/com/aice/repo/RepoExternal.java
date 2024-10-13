package com.aice.repo;

import com.aice.dao.external.DaoExtApi;
import com.aice.dao.external.DaoExtApiMeta;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RepoExternal {
    /**
     *  TABLE : tbl_conf_ext_api
     */
    @Insert({"""
<script>
/*RepoExternal.insertExtApi*/
INSERT INTO tbl_conf_ext_api (
    fk_company
    ,api_type
    ,disp_name
    ,use_yn
    ,fk_Writer
    ,fd_regdate
) VALUES (
    #{fkCompany}
    ,#{apiType}
    ,#{dispName}
    ,#{useYn}
    ,#{fkWriter}
    ,now()
)   
</script>
    """})
    @Options(useGeneratedKeys = true, keyProperty = "pkConfExtApi")
    void insertExtApi(DaoExtApi req);

    @Select({"""
<script>
/*RepoExternal.findExtApi*/
SELECT
    pk_conf_ext_api
    ,fk_company
    ,api_type
    ,disp_name
    ,use_yn
    ,fk_writer
    ,date_format(fd_regdate,'%Y-%m-%d %T') fd_regdate
    ,fk_modifier
    ,date_format(fd_moddate,'%Y-%m-%d %T') fd_moddate
FROM
    tbl_conf_ext_api
WHERE 1=1
    AND fk_company = #{fkCompany}
    AND api_type = #{apiType}
LIMIT 1
</script>
    """})
    DaoExtApi findExtApi(DaoExtApi req);

    @Update({"""
<script>
/*RepoExternal.updateExtApi*/
UPDATE tbl_conf_ext_api SET
    api_type = #{req.apiType}
    ,disp_name = #{req.dispName}
    ,use_yn = #{req.useYn}
    ,fk_modifier = #{req.fkModifier}
    ,fd_moddate = now()
WHERE 1=1
    AND fk_company = #{req.fkCompany}
    AND api_type = #{apiType}
</script>
    """})
    void updateExtApi(String apiType, DaoExtApi req);

    @Delete({"""
<script>
/*RepoExternal.deleteExtApi*/
DELETE FROM tbl_conf_ext_api
WHERE 1=1
    AND fk_company = #{fkCompany}
    AND api_type = #{apiType}
</script>            
"""})
    void deleteExtApi(DaoExtApi req);

    /**
     *  TABLE : tbl_conf_ext_api_meta
     */
    @Insert({"""
<script>
/*RepoExternal.insertExtApiMeta*/
INSERT INTO tbl_conf_ext_api_meta (
    fk_conf_ext_api
    ,meta_key
    ,meta_val
    ,use_yn
    ,fk_writer
    ,fd_regdate
) VALUES (
    #{fkConfExtApi}
    ,#{metaKey}
    ,#{metaVal}
    ,#{useYn}
    ,#{fkWriter}
    ,now()
)   
</script>
    """})
    @Options(useGeneratedKeys = true, keyProperty = "pkConfExtApiMeta")
    void insertExtApiMeta(DaoExtApiMeta req);

    @Select({"""
<script>
/*RepoExternal.findExtApiMeta*/
SELECT
    pk_conf_ext_api_meta
    ,fk_conf_ext_api
    ,meta_key
    ,meta_val
    ,use_yn
    ,fk_writer
    ,date_format(fd_regdate,'%Y-%m-%d %T') fd_regdate
    ,fk_modifier
    ,date_format(fd_moddate,'%Y-%m-%d %T') fd_moddate
FROM
    tbl_conf_ext_api_meta
WHERE 1=1
    AND fk_conf_ext_api = #{fkConfExtApi}
</script>
    """})
    List<DaoExtApiMeta> findExtApiMeta(DaoExtApiMeta req);

    @Update({"""
<script>
/*RepoExternal.updateExtApiMeta*/
UPDATE tbl_conf_ext_api_meta SET
    meta_key = #{req.metaKey}
    ,meta_val = #{req.metaVal}
    ,use_yn = #{req.useYn}
    ,fk_modifier = #{req.fkModifier}
    ,fd_moddate = now()
WHERE 1=1
    AND fk_conf_ext_api = #{req.fkConfExtApi}
    AND meta_key = #{metaKey}
</script>
    """})
    void updateExtApiMeta(String metaKey, DaoExtApiMeta req);

    @Delete({"""
<script>
/*RepoExternal.deleteExtApiMeta*/
DELETE FROM tbl_conf_ext_api_meta
WHERE 1=1
    AND fk_conf_ext_api = #{fkConfExtApi}
    AND meta_key = #{metaKey}
</script>            
"""})
    void deleteExtApiMeta(DaoExtApiMeta req);
}
